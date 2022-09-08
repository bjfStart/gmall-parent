package com.atguigu.gmall.gateway.filter;

import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.common.util.Jsons;
import com.atguigu.gmall.gateway.properties.AuthUrlProperties;
import com.atguigu.gmall.model.user.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.spring.web.json.Json;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

/**
 * 万物皆流
 * 两种流：
 *  1.单数据流【0，1】Mono
 *  2.多数据流 【1，N】 Flux
 * 流的中断信号
 * @author feng
 * @create 2022-09-06 23:40
 */
@Slf4j
@Component
public class GlobalAuthFilter implements GlobalFilter {

    AntPathMatcher matcher = new AntPathMatcher();
    
    @Autowired
    AuthUrlProperties urlProperties;

    @Autowired
    StringRedisTemplate redisTemplate;
    /**
     * 责任链模式
     *  class FilterChain{
     *      Filter[] filters;
     *      int index = 0;
     *
     *      //1.第一种写法
     *      doFilter(req,resp){
     *          for(Filter filter: filters){
     *              filter.doFilter(req,resp,chain);
     *          }
     *      }
     *      //2.第二种写法
     *      doFilter(req,resp){
     *          filters[index++].doFilter(req,resp,this);
     *      }
     *  }
     * @param exchange 有请求和响应
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //1.前置拦截
        String path = exchange.getRequest().getURI().getPath();
        String uri = exchange.getRequest().getURI().toString();
        log.info("{} 请求开始",path);

        //2.无需登录就能访问的资源： 直接放行
        for (String url : urlProperties.getNoAuthUrl()) {
            boolean match = matcher.match(url, path);
            if(match){
                return chain.filter(exchange);
            }
        }
        //静态资源虽然带了token。不用校验token，直接放

        //能走到这，说明不是直接放行的资源

        //3.只要是/api/inner的全部决绝
        for (String url : urlProperties.getDenyUrl()) {
            boolean match = matcher.match(url, path);
            if(match){
                //直接响应json数据即可
                Result<String> result = Result.build("", ResultCodeEnum.PERMISSION);
                return responseResult(result,exchange);
            }
        }

        //4.需要登录的请求：进行权限验证
        for (String url : urlProperties.getLoginAuthUrl()) {
            boolean match = matcher.match(url, path);
            if(match){
                //登录等校验
                //3.1 获取 token 信息【Cookie[token=xxxx]【Header[token=xxx]】】
                String tokenValue = getTokenValue(exchange);
                //3.2 校验 token
                UserInfo info = getTokenUserInfo(tokenValue);
                //3.3 判断用户信息是否正确
                if(info != null){
                    //Redis中由此用户。exchange里面的request的头会新增一个userid
                    ServerWebExchange webExchange = userIdOrTempIdTransport(info,exchange);
                    return chain.filter(webExchange);
                }else{
                    //redis中无次用户【假令牌、token没有，没登录】
                    //重定向到登录页
                    return redirectToCustomPage(urlProperties.getLoginPage() + "?originUrl="+uri,exchange);
                }
            }
        }

        //能走到这儿，既不是静态资源直接放行，也不是必须的才能访问的。就一普通请求
        //普通请求只要带了token，说明可能登录了。只要登录了，就透传用id
        String tokenValue = getTokenValue(exchange);
        UserInfo info = getTokenUserInfo(tokenValue);
        if(!StringUtils.isEmpty(tokenValue) && info == null){
            //假请求直接打回登录
            return redirectToCustomPage(urlProperties.getLoginPage()+"?originUrl="+uri,exchange);
        }
        //普通请求，透传用户id或者时临时id
        exchange = userIdOrTempIdTransport(info,exchange);

        return chain.filter(exchange);


        //4.对登录后的请求进行 user_id 透传
//        Mono<Void> filter = chain.filter(exchange)
//                .doFinally((signalType -> {
//                    log.info("{} 请求结束",path);
//                }));


    }

    /**
     * 响应一个结果
     * @param result
     * @param exchange
     * @return
     */
    private Mono<Void> responseResult(Result<String> result, ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        String jsonStr = Jsons.toStr(result);

        //DataBuffer
        DataBuffer dataBuffer = response.bufferFactory().wrap(jsonStr.getBytes());

        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return response.writeWith(Mono.just(dataBuffer));
    }

    /**
     * 重定向到指定位置
     * @param location
     * @param exchange
     * @return
     */
    private Mono<Void> redirectToCustomPage(String location, ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();

        //1.重定向【302状态码+响应头中 Location:新位置】
        response.setStatusCode(HttpStatus.FOUND);
        //http://passport.gmall.com/login.html?originUrl=http://gmall.com/
        response.getHeaders().add(HttpHeaders.LOCATION,location);

        //2.清除旧的错误的Cookie[tooken] (同名cookie并max-age=0) 解决无限重定向文帝
        ResponseCookie tokenCookie = ResponseCookie.from("token", "777")
                .maxAge(0)
                .path("/")
                .domain(".gmall.com")
                .build();
        response.getCookies().set("token",tokenCookie);

        //3.响应结束
        return response.setComplete();
    }

    /**
     * 用户id透传
     * 看前端有没有带临时id，有的话顺便透传一下
     * @param info
     * @param exchange
     * @return
     */
    private ServerWebExchange userIdOrTempIdTransport(UserInfo info, ServerWebExchange exchange) {

        //请求一旦发来，所有的请求数据都是固定的，不能进行任何修改，只能读取
        ServerHttpRequest.Builder newReqBuilder = exchange.getRequest().mutate();
        //用户登录了
        if(info != null) {
            newReqBuilder.header(SysRedisConst.USERID_HEADER, info.getId().toString());
        }
        //用户没登录
        //获取前端带来的临时id的值
        String userTempId = getUserTempId(exchange);
        newReqBuilder.header(SysRedisConst.USERTEMPID_HEADER,userTempId);
//            //根据原来的请求，封装一个新请求
//            ServerHttpRequest newReq = exchange.getRequest()
//                    .mutate() //变一个新的
//                    .header(SysRedisConst.USERID_HEADER, info.getId().toString())
//                    .header(SysRedisConst.USERTEMPID_HEADER,userTempId)
//                    .build();//添加自己的头

            //放行时传改掉的exchange
            ServerWebExchange webExchange = exchange
                    .mutate()
                    .request(newReqBuilder.build())
                    .response(exchange.getResponse())
                    .build();
            return webExchange;

    }

    /**
     * 获取临时id
     * @param exchange
     * @return
     */
    private String getUserTempId(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        //1.尝试获取类中的临时id
        String  tempId = request.getHeaders().getFirst("userTempId");
        //2.如果头中没有，尝试获取cookie中的值
        if(StringUtils.isEmpty(tempId)){
            HttpCookie httpCookie = request.getCookies().getFirst("userTempId");
            //如果连cookie都没有时，直接获取value，会报空指针异常，需要加判断
            if(httpCookie != null){
                tempId = httpCookie.getValue();
            }
        }
        return tempId;
    }

    /**
     * 根据token的值去redis中查找用户信息
     * @param tokenValue
     * @return
     */
    private UserInfo getTokenUserInfo(String tokenValue) {
         String json = redisTemplate.opsForValue().get(SysRedisConst.LOGIN_USER + tokenValue);
         if(!StringUtils.isEmpty(json)){
             return Jsons.toObj(json,UserInfo.class);
         }
         return null;
    }

    /**
     * 从cookie或请求头中取到 token 对应的值
     * @param exchange
     * @return
     */
    private String getTokenValue(ServerWebExchange exchange) {
        //由于前端乱写，到处可能都有【Cookie[token=xxx] 【Header[token=xxx]】】
        //1.先检查Cookie中有没有这个token
        String tokenValue = "";
        HttpCookie token = exchange.getRequest().getCookies().getFirst("token");
        if(token != null){
            tokenValue = token.getValue();
            return tokenValue;
        }

        //2.说明书cookie中没有
        tokenValue = exchange.getRequest().getHeaders().getFirst("token");
        return tokenValue;
    }


    public static void main(String[] args) throws InterruptedException {
//        //1.集合
//        List<Integer> integers = Arrays.asList(1, 2, 3, 4);
//        //2.每个元素加2
//        Integer integer = integers.stream()
//                .map((t)->t+2)
//                .reduce((a,b) -> a+b)
//                .get();
//        System.out.println(integer);

        //1.单数据流。数据发布者
//        Mono<Integer> just = Mono.just(1);

        Flux<Long> just = Flux.interval(Duration.ofSeconds(1));

        //2.数据订阅者，感兴趣发布者的数据
        just.subscribe((t)->{
            System.out.println("消费者1:"+t);
        });
        just.subscribe((t)->{
            System.out.println("消费者2:"+t);
        });
        just.subscribe((t)->{
            System.out.println("消费者3:"+t);
        });

        Thread.sleep(10000);
    }
}

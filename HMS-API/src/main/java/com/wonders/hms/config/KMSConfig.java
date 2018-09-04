package com.wonders.hms.config;

import com.wonders.hms.util.KMSProperties;
import com.wonders.hms.util.vo.ApiVO;
import com.wonders.hms.util.vo.MessageVO;
import com.wonders.hms.util.vo.RedisVO;
import com.wonders.hms.util.vo.api.AgentVO;
import com.wonders.hms.util.vo.api.InfraVO;
import com.wonders.hms.util.vo.api.agent.Agoda;
import com.wonders.hms.util.vo.api.agent.Booking;
import com.wonders.hms.util.vo.api.agent.Expedia;
import com.wonders.hms.util.vo.api.agent.agoda.Special;
import com.wonders.hms.util.vo.api.agent.booking.Affiliate;
import com.wonders.hms.util.vo.api.agent.expedia.Crosssell;
import com.wonders.hms.util.vo.api.agent.expedia.Ph;
import com.wonders.hms.util.vo.api.infra.Google;
import com.wonders.hms.util.vo.api.infra.google.Place;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class KMSConfig {
   @Autowired
   private KMSProperties kmsProperties;

    @Bean
    public MessageVO messageProp(){
        return Optional.ofNullable(kmsProperties).map(KMSProperties::getMessage).orElseThrow(IllegalStateException::new);
    }

    @Bean
    public ApiVO apiProp(){
       return Optional.ofNullable(messageProp()).map(MessageVO::getApi).orElseThrow(IllegalAccessError::new);
    }
    @Bean
    public RedisVO redisProp(){
        return Optional.ofNullable(messageProp()).map(MessageVO::getRedis).orElseThrow(IllegalStateException::new);
    }

    @Bean
    public AgentVO agentProp() {
        return Optional.ofNullable(apiProp()).map(ApiVO::getAgent).orElseThrow(IllegalStateException::new);
    }
    @Bean
    public Agoda agodaProp(){
        return Optional.ofNullable(agentProp()).map(AgentVO::getAgoda).orElseThrow(IllegalAccessError::new);
    }
    @Bean
    public Special agodaSpecialProp(){
        return Optional.ofNullable(agodaProp()).map(Agoda::getSpecial).orElseThrow(IllegalStateException::new);
    }
    @Bean
    public Expedia expediaProp(){
        return Optional.ofNullable(agentProp()).map(AgentVO::getExpedia).orElseThrow(IllegalStateException::new);
    }
    @Bean
    public Crosssell expediaCrosssell(){
        return Optional.ofNullable(expediaProp()).map(Expedia::getCrosssell).orElseThrow(IllegalStateException::new);
    }
    @Bean
    public Ph expediaPhProp(){
        return Optional.ofNullable(expediaProp()).map(Expedia::getPh).orElseThrow(IllegalStateException::new);
    }
    @Bean(name= "bookingProp", autowire = Autowire.BY_NAME)
    public Booking booking(){
        return Optional.ofNullable(agentProp()).map(AgentVO::getBooking).orElseThrow(IllegalStateException::new);
    }
    @Bean
    public Affiliate affiliate(){
        return Optional.ofNullable(booking()).map(Booking::getAffiliate).orElseThrow(IllegalStateException::new);
    }

    @Bean
    public InfraVO infraProp(){
        return Optional.ofNullable(apiProp()).map(ApiVO::getInfra).orElseThrow(IllegalStateException::new);
    }
    @Bean
    public Google googleProp(){
        return Optional.ofNullable(infraProp()).map(InfraVO::getGoogle).orElseThrow(IllegalAccessError::new);
    }
    @Bean
    public Place placeProp(){
        return Optional.ofNullable(googleProp()).map(Google::getPlace).orElseThrow(IllegalStateException::new);
    }


}

package coronaseat.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@FeignClient(name="cronaseat", url="${api.url.cronaseat}")
public interface CronaseatService {
    @RequestMapping(method= RequestMethod.GET, path="/chkAndModifySeat")
    public boolean modifySeat(@RequestParam("seatId") Long seatId,
    @RequestParam("seatNum") int seatCount);

}


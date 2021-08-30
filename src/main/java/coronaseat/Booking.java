package coronaseat;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="Booking_table")
public class Booking {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private Long seatId;
    private Long seatNum;
    private String status;


    // *************************************************************************
    // 동기호출 : FeignClient를 통한 구현
    // *************************************************************************
    // Booking 수행시, coronaseat에 자리가 없는 상태에서  예약시도 할 경우, 새로운 예약을 못하도록 한다.
    // PostPersist를 하고 예외처리 발생해서 문제 해결함
    // coronaseat coronaService에서 좌석수 체크 (http://localhost:8083/cronaseats의 /chkAndModifySeat) 결과가 false 로 나올 경우
    // 강제 Exception을 발생시켜서 => 서비스 내림, Booking 이 POST되지 않도록 처리한다.
    // *************************************************************************


    @PostPersist
    public void onPostPersist() throws Exception{
        
        boolean rslt = BookingApplication.applicationContext.getBean(coronaseat.external.CronaseatService.class)
        .modifySeat(this.getSeatId(),this.getSeatNum().intValue());
      

        if(rslt){
            System.out.println("=========Booking -Result : true==========");
            this.setStatus("SeatReserved");
            Booked booked = new Booked();
            BeanUtils.copyProperties(this, booked);
            booked.publishAfterCommit();
        }else{
            System.out.println("=========Booking -Result : false==========");
            throw new Exception("Too many seats Count");
        }
        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

     

    }
 
    @PreRemove
    public void onPreRemove(){
        Canceled canceled = new Canceled();
        canceled.setId(this.getId());
        canceled.setStatus("SeatCancled");
        BeanUtils.copyProperties(this, canceled);
        canceled.publishAfterCommit();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }
    public Long getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(Long seatNum) {
        this.seatNum = seatNum;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
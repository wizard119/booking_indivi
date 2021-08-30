package coronaseat.external;

public class Cronaseat {

    private Long id;
    private Long seatId;
    private String seatType;
    private Integer reservableSeat;

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
    public String getSeatType() {
        return seatType;
    }
    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }
    public Integer getReservableSeat() {
        return reservableSeat;
    }
    public void setReservableSeat(Integer reservableSeat) {
        this.reservableSeat = reservableSeat;
    }

}

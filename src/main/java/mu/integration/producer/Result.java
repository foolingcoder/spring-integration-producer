package mu.integration.producer;

/**
 *
 * @author priteela
 */
public class Result {

    public Result(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

}

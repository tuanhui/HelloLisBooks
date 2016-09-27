package tuantuan.com.xiangshengxp.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by chentuanhui on 2016/9/25.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookResult {
    @JsonProperty("errormsg")
    public String errormsg;
    @JsonProperty("errorno")
    public int errorno;
    @JsonProperty("total")
    public int total;
    @JsonProperty("data")
    public List<BookList> data;
}

package tuantuan.com.xiangshengxp.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by chentuanhui on 2016/9/24.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookList {
    @JsonProperty("auto_play")
    public int autoPlay;
    @JsonProperty("award_desc")
    public String awardDesc;
    @JsonProperty("award_open")
    public int awardOpen;
    @JsonProperty("award_text")
    public String awardText;
    @JsonProperty("category_id")
    public int categoryId;
    @JsonProperty("chatgroup_id")
    public int chatgroupId;
    @JsonProperty("description")
    public String description;
    @JsonProperty("detail")
    public DetailBean detail;
    @JsonProperty("id")
    public int id;
    @JsonProperty("latest_program")
    public String latestProgram;
    @JsonProperty("link_id")
    public int linkId;
    @JsonProperty("record_enabled")
    public int recordEnabled;
    @JsonProperty("sale_props")
    public String saleProps;
    @JsonProperty("sale_type")
    public int saleType;
    @JsonProperty("star")
    public int star;
    @JsonProperty("thumbs")
    public ThumbsBean thumbs;
    @JsonProperty("title")
    public String title;
    @JsonProperty("type")
    public String type;
    @JsonProperty("update_time")
    public String updateTime;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DetailBean {
        @JsonProperty("program_count")
        public int programCount;
        @JsonProperty("podcasters")
        public List<PodcastersBean> podcasters;

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class PodcastersBean {
            @JsonProperty("avatar")
            public String avatar;
            @JsonProperty("birthday")
            public String birthday;
            @JsonProperty("description")
            public String description;
            @JsonProperty("fan_num")
            public int fanNum;
            @JsonProperty("id")
            public int id;
            @JsonProperty("location")
            public String location;
            @JsonProperty("nickname")
            public String nickname;
            @JsonProperty("sex")
            public int sex;
            @JsonProperty("signature")
            public String signature;
            @JsonProperty("sns_open")
            public boolean snsOpen;
            @JsonProperty("user_system_id")
            public String userSystemId;
            @JsonProperty("weibo_id")
            public String weiboId;
            @JsonProperty("weibo_name")
            public String weiboName;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ThumbsBean {
        @JsonProperty("200_thumb")
        public String value200Thumb;
        @JsonProperty("400_thumb")
        public String value400Thumb;
        @JsonProperty("800_thumb")
        public String value800Thumb;
        @JsonProperty("large_thumb")
        public String largeThumb;
        @JsonProperty("medium_thumb")
        public String mediumThumb;
        @JsonProperty("small_thumb")
        public String smallThumb;
    }
}

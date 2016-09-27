package tuantuan.com.xiangshengxp.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by chentuanhui on 2016/9/24.
 */

public class BookDetail {
    @JsonProperty("chatgroup_id")
    public int chatgroupId;
    @JsonProperty("description")
    public String description;
    @JsonProperty("detail")
    public DetailBean detail;
    @JsonProperty("duration")
    public int duration;
    @JsonProperty("id")
    public int id;
    @JsonProperty("mediainfo")
    public MediainfoBean mediainfo;
    @JsonProperty("redirect_url")
    public String redirectUrl;
    @JsonProperty("sequence")
    public int sequence;
    @JsonProperty("thumbs")
    public Object thumbs;
    @JsonProperty("title")
    public String title;
    @JsonProperty("type")
    public String type;
    @JsonProperty("update_time")
    public String updateTime;

    public static class DetailBean {
    }

    public static class MediainfoBean {
        @JsonProperty("duration")
        public int duration;
        @JsonProperty("id")
        public int id;
        @JsonProperty("bitrates_url")
        public List<BitratesUrlBean> bitratesUrl;

        public static class BitratesUrlBean {
            @JsonProperty("bitrate")
            public int bitrate;
            @JsonProperty("file_path")
            public String filePath;
            @JsonProperty("qetag")
            public String qetag;
        }
    }
}

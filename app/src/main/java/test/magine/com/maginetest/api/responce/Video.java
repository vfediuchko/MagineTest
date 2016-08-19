package test.magine.com.maginetest.api.responce;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Video response Data Transfer Object
 */
public class Video {
    public String subtitle;
    public List<String> sources;
    public String thumb;
    @SerializedName("image-480x270")
    public String imageSmall;
    @SerializedName("image-780x1200")
    public String imageBig;
    public String title;
    public String studio;
}

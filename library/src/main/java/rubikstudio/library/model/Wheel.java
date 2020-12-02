package rubikstudio.library.model;

import java.util.List;

public class Wheel {

    private String id;
    private List<LuckyItem> mLuckyItemList;
    private String title;
    private int round;


    public Wheel(String id, String title,List<LuckyItem> mLuckyItemList,int round) {
        this.id= id;
        this.title= title;
        this.mLuckyItemList= mLuckyItemList;
        this.round=round;
    }
    public String getId() {
        return id;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<LuckyItem> getmLuckyItemList() {
        return mLuckyItemList;
    }
}

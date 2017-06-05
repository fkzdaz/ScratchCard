package com.example.kz.scratchcard.view.rubble;

//抽奖信息类
public class LotteryInfo {
    // 中奖类型，可以叠加，也就是一等奖二等奖可以一起中,中了多个奖显示最高奖项。
    public final static int TYPE_FIRST = 0x00000001;
    public final static int TYPE_SECOND = 0x00000002;
    public final static int TYPE_THIRD = 0x00000004;
    public final static int TYPE_NONE = 0x00000008;
    // 用于设置type做的模板
    public final int TYPE_MASK = TYPE_FIRST | TYPE_SECOND | TYPE_THIRD
            | TYPE_NONE;

    private String text;
    private int type;
    private String shareText;
    private double scratchPercentage;

    public LotteryInfo(int t, String text, String shareText) {
        setType(t);
        setText(text);
        setShareText(shareText);
    }

    public void setType(int t) {
        if ((t & (~TYPE_MASK)) == 0) {
            return;
        }
        type = t & (~TYPE_MASK);
    }

    public int getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getShareText() {
        return shareText;
    }

    public void setShareText(String shareText) {
        this.shareText = shareText;
    }

    public double getScratchPercentage() {
        return scratchPercentage;
    }

    public void setScratchPercentage(double scratchPercentage) {
        this.scratchPercentage = scratchPercentage;
    }

}

package com.example.kz.scratchcard.view.rubble;

import java.util.Random;

public class LotteryManage {
	public static LotteryInfo getRandomLottery() {
		LotteryInfo info = null;
		Random r = new Random();
		int num = r.nextInt(1000);
		if (num < 10) {
			info = new LotteryInfo(LotteryInfo.TYPE_FIRST, "一等奖",
					"我在幸运大转盘得了1等奖，吼吼！，亲们要一起来么，请访问大镇官网");
		} else if (num < 30) {
			info = new LotteryInfo(LotteryInfo.TYPE_SECOND, "二等奖",
					"我在幸运大转盘得了2等奖，吼吼！，亲们要一起来么，请访问大镇官网");
		} else if (num < 100) {
			info = new LotteryInfo(LotteryInfo.TYPE_THIRD, "三等奖",
					"我在幸运大转盘得了3等奖，吼吼！，亲们要一起来么，请访问大镇官网");
		} else {
			info = new LotteryInfo(LotteryInfo.TYPE_NONE, "再接再厉",
					"我在玩幸运大转盘，不过人品不太好，没中奖，亲们要一起来么，请访问大镇官网");
		}
		return info;
	}
}

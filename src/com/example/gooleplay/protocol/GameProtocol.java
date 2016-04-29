package com.example.gooleplay.protocol;

import com.example.gooleplay.bean.GameDataBean;

public class GameProtocol extends BaseProtocol<GameDataBean> {

	@Override
	protected GameDataBean parseData(String json) {
		return null;
	}

	@Override
	protected String getKey() {
		return null;
	}
	
}

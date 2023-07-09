package com.amp.backend.service.ranklist;

import com.alibaba.fastjson2.JSONObject;

public interface GetRanklistService {
    JSONObject getList(Integer page);
}

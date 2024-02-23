package com.feb.test.dao;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

@Repository
public interface MemberDao {
	public int join(HashMap<String, String> params);
	public int searchId(String id);
}

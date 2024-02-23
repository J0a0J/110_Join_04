package com.feb.test.service;

import java.util.HashMap;

import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feb.test.dao.MemberDao;
import com.feb.test.util.Sha512Encoder;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;
	public MemberService() {
		System.out.println("!!!!!!!!!!!!!!!1 	MEMBER SERVICE 		!!!!!!!!!!!!!");
	}

	public int join(HashMap<String, String> params) {
		String id = params.get("memberId");
		// 회원ID 6자 이하 가입 불가
		if (id.length() <= 6) {
			return -1;
		}
		
        // ID 중복 확인해보기
		int dupCheck = searchId(id);
		if(dupCheck != 0) {
			return -2;
		}
		
		// 조건 충족 후 비밀번호 암호화 
		Sha512Encoder encoder = Sha512Encoder.getInstance();
		String passwd = params.get("passwd");
		String encodeTxt = encoder.getSecurePassword(passwd);
		params.put("passwd", encodeTxt);
		
		// id 6자 이하 아니고 중복 아닐 때 정보 입력
		return memberDao.join(params);
	}
	
	public int searchId(String id) {
		int result;
		try {
			result =  memberDao.searchId(id);
//			null 이 반환되어야 중복이 없는건데 그러면 에러가 생기니 try-catch 사
		} catch(BindingException e) {
			// 중복 없을 때 
			return result = 0;
		}
		// 중복 회원이 있을 때 
		return (int)result;

		
		
	}
}

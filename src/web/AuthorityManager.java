package web;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import exceptions.AccessDeniedException;

public class AuthorityManager {

	private static AuthorityManager authorityManager;
	
	private AuthorityManager(){}
	
	public static AuthorityManager instance(){
		if(authorityManager == null) authorityManager = new AuthorityManager();
		return authorityManager;
	}
	
	private boolean hasAccess = false;
	public boolean hasAuthorities(String[] authorities){
		hasAccess = false;
		/*if(SecurityContextHolder.getContext().getAuthentication() != null){
			
		SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach((GrantedAuthority ga) -> {
			boolean hasAccess = false;
			for(String authority : authorities){
				if(ga.getAuthority().equals(authority)) hasAccess = true;
			}
			if(!hasAccess) throw new AccessDeniedException();
			});
		} else return false;*/
		if(SecurityContextHolder.getContext().getAuthentication() != null){
			
			SecurityContextHolder.
			getContext().
			getAuthentication().
			getAuthorities().
			forEach((GrantedAuthority ga) -> {
				for(String authority : authorities){
					if(ga.getAuthority().equals(authority)) {hasAccess = true;return;}
				}
			});
			}
		
		if(hasAccess) return true;
			else return false;
	}
	
	public String getCurrentUsername(){
		if(SecurityContextHolder.getContext().getAuthentication() != null)
		return  SecurityContextHolder.getContext().getAuthentication().getName();
		else return "User not found";
	}
	
	private boolean isAuthority;
	public boolean isAuthority(String authority){
		isAuthority = false;
		SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach((GrantedAuthority ga) -> {
			if(ga.getAuthority().equals(authority)) isAuthority =  true;
				else isAuthority =  false;
		});
		return isAuthority;
	}
	
	public boolean compareGivenUsernameWithPrincipalUsername(String username){
		String principalUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		if(username.equals(principalUsername)) return true;
			else return false;
	}
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	


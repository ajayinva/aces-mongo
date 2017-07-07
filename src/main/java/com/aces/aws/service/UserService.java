package com.aces.aws.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aces.aws.domain.Notes;
import com.aces.aws.entity.ProductPlan;
import com.aces.aws.entity.QuestionNote;
import com.aces.aws.entity.User;
import com.aces.aws.entity.UserProduct;
import com.aces.aws.entity.UserToken;
import com.aces.aws.infra.AcesException;
import com.aces.aws.infra.GlobalFunctions;
import com.aces.aws.repositories.UserRepository;
import com.aces.aws.repositories.UserTokenRepository;

/**
 * 
 * @author aagarwal
 *
 */
@Service
@Transactional
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    
    private static final String ERROR_KEY_USER_EXISTS = "userAlreadyExist";
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private UserTokenRepository passwordResetTokenRepository;
    
    @Autowired
    private ProductService productService;
    /**
     * 
     * @param user
     * @return
     */
    public User createUser(User user) {
        User localUser = userRepository.findByUserName(user.userName);
        if (localUser != null) {
        	LOGGER.info("User with username {} already exist.",user.userName);
        	throw new AcesException(ERROR_KEY_USER_EXISTS);        	
        } else {            
            user.password = passwordEncoder.encode(user.password); 
            user.products.add(new UserProduct(1L, 2L));
            localUser = userRepository.save(user);
        }
        return localUser;
    }
    /**
     * 
     * @param user
     */
    public void updatePassword(String userName, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        User localUser = userRepository.findByUserName(userName);
        localUser.password = encodedPassword;
        userRepository.save(localUser);               
        List<UserToken> resetTokens = passwordResetTokenRepository.findAllByUserName(userName);
        if (!resetTokens.isEmpty()) {
            passwordResetTokenRepository.delete(resetTokens);
        }
    }
    /**
     * 
     * @param user
     */
    public void activateAccount(String userName) {    
    	User localUser = userRepository.findByUserName(userName);
    	localUser.enabled = true;
        userRepository.save(localUser);        
        List<UserToken> resetTokens = passwordResetTokenRepository.findAllByUserName(userName);
        if (!resetTokens.isEmpty()) {
            passwordResetTokenRepository.delete(resetTokens);
        }
    }
    /**
     * 
     */
    public List<ProductPlan> getUserProductPlans(){    	
    	List<ProductPlan> planList = new ArrayList<>();    	
		User user = GlobalFunctions.getCurrentUser();
		User localUser = userRepository.findByUserName(user.userName);	
		for(UserProduct userProduct : localUser.products){
			planList.add(productService.getProductPlan(userProduct.planId));
		}
		return planList;
    }    
    /**
     * 
     */
    public QuestionNote getQuestionNotes(Long questionId){    	    	   
		User user = GlobalFunctions.getCurrentUser();
		User localUser = userRepository.findByUserName(user.userName);	
		List<QuestionNote> notes = localUser.notes;	
		if(notes!=null && !notes.isEmpty()){			
			Optional<QuestionNote> questionNote = notes.stream().filter(note -> note.questionId==questionId).findFirst();
			if(questionNote.isPresent()){
				return questionNote.get();
			}
		}
		return null;
    }
    /**
     * 
     * @param questionId
     */
    public void saveNotes(Notes notes){    	    	 
    	User localUser = userRepository.findByUserName(GlobalFunctions.getCurrentUser().userName);
    	localUser.notes.add(new QuestionNote(notes.getQuestionId(),notes.getNotes()));
    	userRepository.save(localUser);
    }
}

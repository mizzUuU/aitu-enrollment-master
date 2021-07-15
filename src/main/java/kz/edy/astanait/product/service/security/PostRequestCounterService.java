package kz.edy.astanait.product.service.security;

import kz.edy.astanait.product.model.security.PostRequestCounter;
import kz.edy.astanait.product.repository.UserRepository;
import kz.edy.astanait.product.repository.security.PostRequestCounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static kz.edy.astanait.product.constant.SecurityConstant.POST_HTTP_METHOD;

@Service
public class PostRequestCounterService {

    private final PostRequestCounterRepository postRequestCounterRepository;
    private final UserRepository userRepository;

    private static final int RequestLimit = 150;
    private static final int GlobalExpirationTime = 43200000;
    private static final int ExpirationTimeAfterBlock = 1800000;

    @Autowired
    public PostRequestCounterService(PostRequestCounterRepository postRequestCounterRepository, UserRepository userRepository) {
        this.postRequestCounterRepository = postRequestCounterRepository;
        this.userRepository = userRepository;
    }

    public boolean validatePostRequest(String email, HttpServletRequest request) {
        if (request.getMethod().equalsIgnoreCase(POST_HTTP_METHOD)) {
            Optional<PostRequestCounter> postRequestCounter = postRequestCounterRepository.findByUserEmail(email);
            if (postRequestCounter.isPresent()) {
                if (checkExpiration(postRequestCounter.get())) {
                    return true;
                }
                return exceededLimit(postRequestCounter.get());
            }
            else {
                createPostRequestCounter(email);
            }
        }
        return true;
    }

    private void createPostRequestCounter(String email) {
        PostRequestCounter postRequestCounter = new PostRequestCounter();
        userRepository.findUserByEmail(email).ifPresent(postRequestCounter::setUser);
        postRequestCounter.setCounter(1);
        postRequestCounterRepository.save(postRequestCounter);
    }

    private boolean checkExpiration(PostRequestCounter postRequestCounter) {
        if (postRequestCounter.getCreatedDate().atZone(ZoneId.systemDefault()).
                toInstant().toEpochMilli() + GlobalExpirationTime  <= System.currentTimeMillis()) {
            postRequestCounter.setCreatedDate(LocalDateTime.now());
            postRequestCounter.setLastBlockedDate(null);
            postRequestCounter.setCounter(1);
            postRequestCounterRepository.save(postRequestCounter);
            return true;
        }
        return false;
    }

    private boolean checkExpiredBlock(PostRequestCounter postRequestCounter) {
        if (postRequestCounter.getLastBlockedDate()
                .atZone(ZoneId.systemDefault()).
                        toInstant().toEpochMilli() + ExpirationTimeAfterBlock  <= System.currentTimeMillis()) {
            postRequestCounter.setLastBlockedDate(null);
            postRequestCounter.setCounter(1);
            postRequestCounterRepository.save(postRequestCounter);
            return true;
        }
        return false;
    }


    private void createLastBlock (PostRequestCounter postRequestCounter) {
        postRequestCounter.setLastBlockedDate(LocalDateTime.now());
        postRequestCounterRepository.save(postRequestCounter);
    }

    private boolean exceededLimit(PostRequestCounter postRequestCounter) {
        if (postRequestCounter.getCounter() >= RequestLimit) {
            if (postRequestCounter.getLastBlockedDate() != null) {
                return checkExpiredBlock(postRequestCounter);
            }
            else {
                createLastBlock(postRequestCounter);
                return false;
            }
        }
        postRequestCounter.setCounter(postRequestCounter.getCounter() + 1);
        postRequestCounterRepository.save(postRequestCounter);
        return true;
    }
}

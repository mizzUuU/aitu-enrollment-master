package kz.edy.astanait.product.service.secretary;

import kz.edy.astanait.product.exception.domain.UserNotFoundException;
import kz.edy.astanait.product.model.secretary.ConfirmBlocks;
import kz.edy.astanait.product.repository.secretary.ConfirmBlocksRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfirmBlocksService {

    private final ConfirmBlocksRepository confirmBlocksRepository;

    public ConfirmBlocksService(ConfirmBlocksRepository confirmBlocksRepository) {
        this.confirmBlocksRepository = confirmBlocksRepository;
    }

    public void confirmBlocks(Long id) {
        Optional<ConfirmBlocks> confirmBlocks = confirmBlocksRepository.findByUserId(id);
        if (confirmBlocks.isPresent()) {
            confirmBlocks.get().setFirstBlock(true);
            confirmBlocks.get().setSecondBlock(true);
            confirmBlocks.get().setThirdBlock(true);
            confirmBlocks.get().setForthBlock(true);
            confirmBlocksRepository.save(confirmBlocks.get());
        } else {
            throw new UserNotFoundException("confirm block not found by users id: " + id);
        }
    }
}


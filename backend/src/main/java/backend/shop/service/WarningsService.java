package backend.shop.service;

import backend.shop.dto.UserDTO;
import backend.shop.dto.WarningDTO;
import backend.shop.exceptions.OrderNotFoundException;
import backend.shop.exceptions.UserNotFoundException;
import backend.shop.model.Warnings;
import backend.shop.repo.OrdersRepo;
import backend.shop.repo.UsersRepo;
import backend.shop.repo.WarningRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarningsService {
    private final WarningRepo warningRepo;
    private final UsersRepo usersRepo;
    private final OrdersRepo ordersRepo;

    public WarningsService(WarningRepo warningRepo, UsersRepo usersRepo, OrdersRepo ordersRepo) {
        this.warningRepo = warningRepo;
        this.usersRepo = usersRepo;
        this.ordersRepo = ordersRepo;
    }
    @Transactional
    public void addNewWarning(WarningDTO warning) {

        var user = this.usersRepo.findById(warning.userId()).orElseThrow(() -> new UserNotFoundException("Nie znaleziono uzytkownika o id: " + warning.userId()));
        var order = this.ordersRepo.findById(warning.orderId()).orElseThrow(() -> new OrderNotFoundException("Nie znaleziono zamowienia o id: " + warning.orderId()));

        Warnings newWarning = Warnings.builder()
                .issueTopic(warning.issueTopic())
                .affectedProducts(warning.affectedProducts())
                .description(warning.description())
                .expectations(warning.expectations())
                .orderId(order)
                .userId(user)
                .build();

        this.warningRepo.save(newWarning);

    }

    public List<WarningDTO> getAllWarnings() {

        return this.warningRepo.findByIssueStatusNot("zakonczony").stream()
                .map(w ->{
                    if(w.getOrderId() == null) throw new OrderNotFoundException("Nie znaleziono identyfikatora zamowienia o numerze: " + w.getWarningId());
                    if(w.getUserId() == null) throw new UserNotFoundException("Nie znaleziono identyfikatora klienta o numerze: " + w.getWarningId());

                    return new WarningDTO(
                        w.getWarningId(), w.getIssueTopic(),
                        w.getAffectedProducts(), w.getDescription(),
                        w.getExpectations(),
                        w.getOrderId().getOrderId(),
                        w.getUserId().getUserId()
                        );
                })
                .toList();

    }
}

package me.sathish.fuzzy.controllers;

import com.github.javafaker.Faker;
import com.intuit.fuzzymatcher.component.MatchService;
import com.intuit.fuzzymatcher.domain.Document;
import com.intuit.fuzzymatcher.domain.Element;
import com.intuit.fuzzymatcher.domain.ElementType;
import com.intuit.fuzzymatcher.domain.Match;
import lombok.extern.slf4j.Slf4j;
import me.sathish.fuzzy.entities.AccountHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/randomdata")
@Slf4j
public class RandomDataController {
    Faker faker = Faker.instance();
    //        List<Document> documentList= accountHolderList.stream().map(allAccounts -> {
//            return new Document.Builder(allAccounts.get[0]).
//                addElement(new Element.Builder<String>().
//                    setValue(accountHolder[1]).setType(ElementType.NAME).createElement()).createDocument();
//        }).collect(Collectors.toList());
//    Map<String, List<Match<Document>>> result = matchService.applyMatchByDocId(documentList);)

    @GetMapping
    public List<AccountHolder> getAllAccounts() {
        List<AccountHolder> accountHolderList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            AccountHolder accountHolder = new AccountHolder();
            accountHolder.setId(faker.idNumber().ssnValid());
            accountHolder.setFirstName(faker.name().firstName());
            accountHolder.setLastName(faker.name().lastName());
            accountHolder.setAddressLine1(faker.address().fullAddress());
            accountHolder.setAddressLine2(faker.address().streetAddressNumber());
            accountHolderList.add(accountHolder);
        }
        AccountHolder accountHolderDup = accountHolderList.get(1000);
        accountHolderDup.setLastName("Jayapal");
        accountHolderList.add(accountHolderDup);
        AccountHolder accountHolderDup2 = accountHolderList.get(6000);
        accountHolderDup.setLastName("Jayapal");
        accountHolderList.add(accountHolderDup2);

        List<Document> accountDocHolderList = accountHolderList.stream().map(singleDoc -> {
                return new Document.Builder(singleDoc.getId()).
                    addElement(new Element.Builder<String>().setValue(new StringBuilder(singleDoc.getLastName()).append(" ").append(singleDoc.getFirstName()).toString()).setType(ElementType.NAME).createElement()).
                    addElement(new Element.Builder<String>().setValue(new StringBuilder(singleDoc.getAddressLine1()).append(" ").append(singleDoc.getAddressLine2()).toString()).setType(ElementType.ADDRESS).createElement())
                    .createDocument();
            })
            .collect(Collectors.toList());
        MatchService matchService1 = new MatchService();
        Map<String, List<Match<Document>>> result = matchService1.applyMatchByDocId(accountDocHolderList);
        result.entrySet().forEach(entry -> {
            entry.getValue().forEach(documentMatch -> {
                System.out.println("Data :" + documentMatch.getData() + "Matched with:" + documentMatch.getMatchedWith());
            });
        });
        return accountHolderList;
    }
}



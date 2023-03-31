package deu.hellostock.service;

import deu.hellostock.repository.CorpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CorpService {
    private final CorpRepository corpRepository;




}

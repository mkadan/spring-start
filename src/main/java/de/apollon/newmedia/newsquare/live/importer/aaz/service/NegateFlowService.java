package de.apollon.newmedia.newsquare.live.importer.aaz.service;

import org.springframework.stereotype.Service;

@Service
public class NegateFlowService {

    public boolean isNegative(int i) {
        return i < 0;
    }

    public int negate(int i) {
        return -i;
    }

    public String str(int i) {
        return String.valueOf(i);
    }
}

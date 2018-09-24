package org.solo.importing;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PolishSignsRemoverTest {

    @Test
    public void shouldMapPolishSigns(){
        String withPolishSigns = "Ala mą kóta i Żyli dŁugo i szczęŚliwie.";
        String withoutPolishSigns = "Ala ma kota i Zyli dLugo i szczeSliwie.";
        String mapped = new PolishSignsRemover().map(withPolishSigns);
        assertEquals(withoutPolishSigns, mapped);
    }

}
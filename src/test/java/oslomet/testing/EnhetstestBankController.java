
package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.bind.annotation.RequestBody;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;


    @Test
    public void hentTransaksjoner_loggetInn() {
        Konto tran = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        when(sjekk.loggetInn()).thenReturn("01010110523");
        Mockito.when(repository.hentTransaksjoner(anyString(),anyString(),anyString())).thenReturn(tran);

        Konto resultat = bankController.hentTransaksjoner("123123123","24 Februar","25 Februar");
        assertEquals(tran,resultat);

    }
    @Test
    public void hentTransaksjoner_ikkeLoggetInn(){
        when(sjekk.loggetInn()).thenReturn(null);
        Konto resultat = bankController.hentTransaksjoner(null,null,null);
        assertNull(resultat);
    }


    @Test
    public void hentKonti_LoggetInn()  {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        Mockito.when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentSaldi_loggetInn() {
        List<Konto> saldi = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        Konto konto2 = new Konto("105010123456", "12345678901",
                720, "Lønnskonto", "NOK", null);

        saldi.add(konto1);
        saldi.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        Mockito.when(repository.hentSaldi(anyString())).thenReturn(saldi);

        List<Konto> resultat = bankController.hentSaldi();

        assertEquals(saldi, resultat);
    }

    @Test
    public void hentSaldi_ikkeLoggetInn()  {
        when(sjekk.loggetInn()).thenReturn(null);
        List<Konto> resultat = bankController.hentSaldi();
        assertNull(resultat);
    }


    @Test
    public void registrerBetaling_loggetInn(){
        Transaksjon regbet = new Transaksjon(22,"asdads",22,"asdasd","asda","asda","asda");
        when(sjekk.loggetInn()).thenReturn("01010110523");
        Mockito.when(repository.registrerBetaling((any(Transaksjon.class)))).thenReturn("OK");
        String resultat = bankController.registrerBetaling(regbet);
        assertEquals("OK",resultat);
    }

    @Test
    public void registrerBetaling_ikkeLoggetInn(){
        Transaksjon regbet = new Transaksjon(22,"asdads",22,"asdasd","asda","asda","asda");
        when(sjekk.loggetInn()).thenReturn("Feil");
        Mockito.when(repository.registrerBetaling((any(Transaksjon.class)))).thenReturn("Feil");
        String resultat = bankController.registrerBetaling(regbet);
        assertEquals("Feil",resultat);

    }

    @Test
    public void hentBetalinger_LoggetInn() {
        List<Transaksjon> hentBet = new ArrayList<>();

        Transaksjon tran1 = new Transaksjon(7, "123", 8, "123", "123", "123", "123123");
        Transaksjon tran2 = new Transaksjon(7, "1234", 8, "1234", "1234", "1234", "123124");

        hentBet.add(tran1);
        hentBet.add(tran2);

        when(sjekk.loggetInn()).thenReturn("123123");
        Mockito.when(repository.hentBetalinger(anyString())).thenReturn(hentBet);

        List<Transaksjon> resultat = bankController.hentBetalinger();
        assertEquals(hentBet, resultat);
    }

    @Test
    public void hentBetalinger_IkkeLoggetInn(){
        when(sjekk.loggetInn()).thenReturn(null);

        List<Transaksjon> resultat = bankController.hentBetalinger();

        assertNull(resultat);

    }




    @Test
    public void utforBetaling_LoggetInn(){
        List<Transaksjon> hentBet = new ArrayList<>();

        Transaksjon tran1 = new Transaksjon(7, "123", 8, "123", "123", "123", "123123");
        Transaksjon tran2 = new Transaksjon(7, "1234", 8, "1234", "1234", "1234", "123124");

        hentBet.add(tran1);
        hentBet.add(tran2);

        when(sjekk.loggetInn()).thenReturn("123123");
        Mockito.when(repository.utforBetaling(anyInt())).thenReturn("OK");
        Mockito.when(repository.hentBetalinger(anyString())).thenReturn(hentBet);


        List<Transaksjon> resultat = bankController.utforBetaling(7);
        assertEquals("OK", resultat);


        /*

        List<Transaksjon> hentBet = new ArrayList<>();

       Transaksjon tran1 = new Transaksjon(7, "123", 8, "123", "123", "123", "123123");
        Transaksjon tran2 = new Transaksjon(2, "1234", 8, "1234", "1234", "1234", "123124");

        hentBet.add(tran1);
        hentBet.add(tran2);

        when(sjekk.loggetInn()).thenReturn("123123");
        when(repository.utforBetaling(anyInt())).thenReturn("OK");

        List<Transaksjon> resultat = bankController.utforBetaling(7);
        assertEquals("OK", resultat);*/

    }






    @Test
    public void hentKundeInfo_loggetInn() {

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        Mockito.when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }


    @Test
    public void hentKundeInfo_IkkeloggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }


    @Test
    public void endreKundeInfo_loggetInn(){
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        Mockito.when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");
        String resultat = bankController.endre(enKunde);

        assertEquals("OK",resultat);

    }

    @Test
    public void endreKundeInfo_IkkeloggetInn(){
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");
        when(sjekk.loggetInn()).thenReturn(null);
        String resultat = bankController.endre(enKunde);
        assertNull(resultat);
    }


}


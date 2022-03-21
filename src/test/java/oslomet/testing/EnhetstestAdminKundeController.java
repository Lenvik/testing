package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKundeController {

    @InjectMocks
    // denne skal testes
    private AdminKundeController adminKundeController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentAlle_OK() {
        //arrange
        List<Kunde> hentAlleOK = new ArrayList<>();

        Kunde Kunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");
        Kunde Kunde2 = new Kunde("12345678901",
                "Per", "Hansen", "Osloveien 82", "1234",
                "Oslo", "12345678", "HeiHei");

        hentAlleOK.add(Kunde1);
        hentAlleOK.add(Kunde2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        Mockito.when(repository.hentAlleKunder()).thenReturn(hentAlleOK);

        //act
        List<Kunde> resultat = adminKundeController.hentAlle();

        //assert
        assertEquals(hentAlleOK, resultat);
    }

    @Test
    public void hentAlle_feil() {
        //arrange
        Mockito.lenient().when(repository.hentAlleKonti()).thenReturn(null); //lenient() for å fjerne UnnecessaryStubbingException

        //act
        List<Kunde> resultat = adminKundeController.hentAlle();

        //assert
        assertNull(resultat);
    }

    @Test
    public void lagreKunde_loggetInn() {
        //arrange
        Kunde nyKunde = new Kunde("01030110523",
                "Ola", "Nordmann", "Bergensveien 123", "4455",
                "Bergen", "11112222", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01030110523");

        Mockito.when(repository.registrerKunde((any(Kunde.class)))).thenReturn("OK");

        //act
        String resultat = adminKundeController.lagreKunde(nyKunde);

        //assert
        assertEquals("OK", resultat);
    }

    @Test
    public void lagreKunde_ikkeLoggetInn() {
        //arrange
        Kunde nyKunde = new Kunde("01030110523",
                "Ola", "Nordmann", "Bergensveien 123", "4455",
                "Bergen", "11112222", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01030110523");

        Mockito.when(repository.registrerKunde((any(Kunde.class)))).thenReturn("Feil");

        //act
        String resultat = adminKundeController.lagreKunde(nyKunde);

        //assert
        assertEquals("Feil", resultat);
    }

    @Test
    public void endreKunde_loggetInn() {
        //arrange
        Kunde nyKunde = new Kunde("12345678901",
                "Per", "Hansen", "Osloveien 82", "1234",
                "Oslo", "12345678", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        Mockito.when(repository.endreKundeInfo((any(Kunde.class)))).thenReturn("OK");

        //act
        String resultat = adminKundeController.endre(nyKunde);

        //assert
        assertEquals("OK", resultat);
    }

    @Test
    public void endreKunde_ikkeLoggetInn() {
        //arrange
        Kunde nyKunde = new Kunde("12345678901",
                "Per", "Hansen", "Osloveien 82", "1234",
                "Oslo", "12345678", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        Mockito.when(repository.endreKundeInfo((any(Kunde.class)))).thenReturn("Feil");

        //act
        String resultat = adminKundeController.endre(nyKunde);

        //assert
        assertEquals("Feil", resultat);
    }

    @Test
    public void slettKunde_loggetInn() {
        //arrange
        String slettKundeOla = "105030123456";

        when(sjekk.loggetInn()).thenReturn("01010110523");

        Mockito.when(repository.slettKunde((anyString()))).thenReturn("OK");

        //act
        String resultat = adminKundeController.slett(slettKundeOla);

        //assert
        assertEquals("OK", resultat);
    }

    @Test
    public void slettKunde_ikkeLoggetInn() {
        //arrange
        String slettKundeOla = "105030123456";

        when(sjekk.loggetInn()).thenReturn("01010110523");

        Mockito.when(repository.slettKunde((anyString()))).thenReturn("Feil");

        //act
        String resultat = adminKundeController.slett(slettKundeOla);

        //assert
        assertEquals("Feil", resultat);
    }
}

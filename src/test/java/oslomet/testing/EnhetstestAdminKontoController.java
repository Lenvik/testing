package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {

    @InjectMocks
    // denne skal testes
    private AdminKontoController adminKontoController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentAlle_OK() {
        //arrange
        List<Konto> hentAlleOK = new ArrayList<>();

        Konto konto1 = new Konto("01010110523", "105010123456", 720.00, "Lønnskonto",
                "NOK", null);
        Konto konto2 = new Konto("01010110523", "105020123456", 100500.00, "Sparekonto",
                "NOK", null);

        Konto konto3 = new Konto("01010110523", "22334412345", 10234.50, "Brukskonto",
                "NOK", null);

        hentAlleOK.add(konto1);
        hentAlleOK.add(konto2);
        hentAlleOK.add(konto3);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        Mockito.when(repository.hentAlleKonti()).thenReturn(hentAlleOK);

        //act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        //assert
        assertEquals(hentAlleOK, resultat);
    }

    @Test
    public void hentAlle_feil() {
        //arrange
        Mockito.lenient().when(repository.hentAlleKonti()).thenReturn(null); //lenient() for å fjerne UnnecessaryStubbingException

        //act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        //assert
        assertNull(resultat);
    }

    @Test
    public void registrerKonto_loggetInn() {
        //arrange
        Konto nyKonto = new Konto("01010110523", "105030123456", 1000.00, "Pensjonskonto",
                "NOK", null);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        Mockito.when(repository.registrerKonto((any(Konto.class)))).thenReturn("OK");

        //act
        String resultat = adminKontoController.registrerKonto(nyKonto);

        //assert
        assertEquals("OK", resultat);
    }

    @Test
    public void registrerKonto_ikkeLoggetInn() {
        //arrange
        Konto nyKonto = new Konto("01010110523", "105030123456", 1000.00, "Pensjonskonto",
                "NOK", null);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        Mockito.when(repository.registrerKonto((any(Konto.class)))).thenReturn("Feil");

        //act
        String resultat = adminKontoController.registrerKonto(nyKonto);

        //assert
        assertEquals("Feil", resultat);
    }

    @Test
    public void endreKonto_loggetInn() {
        //arrange
        Konto nyKonto = new Konto("01010110523", "105030123456", 100500.00, "Sparekonto",
                "NOK", null);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        Mockito.when(repository.endreKonto((any(Konto.class)))).thenReturn("OK");

        //act
        String resultat = adminKontoController.endreKonto(nyKonto);

        //assert
        assertEquals("OK", resultat);
    }

    @Test
    public void endreKonto_ikkeLoggetInn() {
        //arrange
        Konto nyKonto = new Konto("01010110523", "105030123456", 100500.00, "Sparekonto",
                "NOK", null);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        Mockito.when(repository.endreKonto((any(Konto.class)))).thenReturn("Feil");

        //act
        String resultat = adminKontoController.endreKonto(nyKonto);

        //assert
        assertEquals("Feil", resultat);
    }

    @Test
    public void slettKonto_loggetInn() {
        //arrange
        String slettPensjonsKonto = "105030123456";

        when(sjekk.loggetInn()).thenReturn("01010110523");

        Mockito.when(repository.slettKonto((anyString()))).thenReturn("OK");

        //act
        String resultat = adminKontoController.slettKonto(slettPensjonsKonto);

        //assert
        assertEquals("OK", resultat);
    }

    @Test
    public void slettKonto_ikkeLoggetInn() {
        //arrange
        String slettPensjonskonto = "105030123456";

        when(sjekk.loggetInn()).thenReturn("01010110523");

        Mockito.when(repository.slettKonto((anyString()))).thenReturn("Feil");

        //act
        String resultat = adminKontoController.slettKonto(slettPensjonskonto);

        //assert
        assertEquals("Feil", resultat);
    }
}

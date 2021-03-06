package oslomet.testing;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockHttpSession;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.doAnswer;

@RunWith(MockitoJUnitRunner.class)
public class SikkerhetsController {
    @InjectMocks
    private Sikkerhet sikkerhetsController;

    @Mock
    private BankRepository repository;

    @Mock
    MockHttpSession session;

    @Before

    public void initSession(){
        Map<String,Object> attributes = new HashMap<String,Object>();

        doAnswer(new Answer<Object>(){
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                return attributes.get(key);
            }
        }).when(session).getAttribute(anyString());

        doAnswer(new Answer<Object>(){
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                Object value = invocation.getArguments()[1];
                attributes.put(key, value);
                return null;
            }
        }).when(session).setAttribute(anyString(), any());
    }

    @Test
    public void sjekkLogInn_OK(){
        when(repository.sjekkLoggInn(anyString(),anyString())).thenReturn("OK");
        String resultat = sikkerhetsController.sjekkLoggInn("12345678901","Passord");
        assertEquals("OK",resultat);
    }

    @Test
    public void sjekkLogInn_IkkeOkEn(){
        when(repository.sjekkLoggInn(anyString(),anyString())).thenReturn("Feil i personnummer");
        String resultat = sikkerhetsController.sjekkLoggInn("12345678901","Passord");
        assertEquals("Feil i personnummer eller passord",resultat);
    }
    @Test
    public void sjekkLogInn_IkkeOkTo(){
        when(repository.sjekkLoggInn(anyString(),anyString())).thenReturn("Feil i passord");
        String resultat = sikkerhetsController.sjekkLoggInn("12345678901","Passord");
        assertEquals("Feil i personnummer eller passord",resultat);
    }


    @Test
    public void sjekkLogInnAdmin_Ok(){
        String resultat = sikkerhetsController.loggInnAdmin("Admin","Admin");
        assertEquals("Logget inn",resultat);
    }
   @Test
    public void sjekkLogInnAdmin_IkkeOk(){
        session.setAttribute("Innlogget",null);
        String resultat = sikkerhetsController.loggInnAdmin("","");
        assertEquals("Ikke logget inn",resultat);
    }



    @Test
    public void sjekkLoggetInn_OK(){
        session.setAttribute("Innlogget","12345678901");
        String resultat = sikkerhetsController.loggetInn();
        assertEquals("12345678901",resultat);
    }

    @Test
    public void sjekkLoggetInn_IkkeOK(){
        session.setAttribute("Innlogget",null);
        String resultat = sikkerhetsController.loggetInn();
        assertNull(resultat);
    }

}


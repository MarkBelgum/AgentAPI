package learn.field_agent.domain;

import learn.field_agent.data.SecurityClearanceRepository;
import learn.field_agent.models.Agency;
import learn.field_agent.models.SecurityClearance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SecurityClearanceServiceTest {
    @Autowired
    SecurityClearanceService service;

    @MockBean
    SecurityClearanceRepository repository;

    @Test
    void shouldFindSecret() {
        SecurityClearance expected = makeSecurityClearance();
        when(repository.findById(1)).thenReturn(expected);
        SecurityClearance actual = service.findById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddWhenInvalid() {
        SecurityClearance securityClearance = makeSecurityClearance();
        Result<SecurityClearance> result = service.add(securityClearance);
        assertEquals(ResultType.INVALID, result.getType());

        securityClearance.setSecurityClearanceId(0);
        securityClearance.setName(null);
        result = service.add(securityClearance);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldAddWhenValid() {
        SecurityClearance expected = makeSecurityClearance();
        SecurityClearance arg = makeSecurityClearance();
        arg.setSecurityClearanceId(0);

        when(repository.add(arg)).thenReturn(expected);
        Result<SecurityClearance> result = service.add(arg);
        assertEquals(ResultType.SUCCESS, result.getType());

        assertEquals(expected, result.getPayload());
    }

    @Test
    void shouldUpdate() {
        SecurityClearance securityClearance = new SecurityClearance(2, "Secret Test");

        when(repository.update(securityClearance)).thenReturn(true);
        Result<SecurityClearance> actual = service.update(securityClearance);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldDeleteWhenReferenced() {
        when(repository.deleteById(1)).thenReturn(true);
        Result<SecurityClearance> actual = service.deleteById(1);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotDeleteWhenReferenced() {
        when(repository.deleteById(5)).thenReturn(false);
        Result<SecurityClearance> actual = service.deleteById(5);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    SecurityClearance makeSecurityClearance() {
        //('Secret'),
        SecurityClearance securityClearance = new SecurityClearance();
        securityClearance.setSecurityClearanceId(1);
        securityClearance.setName("Secret");
        return securityClearance;
    }
}

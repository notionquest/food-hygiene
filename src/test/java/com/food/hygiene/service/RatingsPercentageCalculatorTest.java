package com.food.hygiene.service;

import com.food.hygiene.BaseTest;
import com.food.hygiene.model.Ratings;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RatingsPercentageCalculatorTest extends BaseTest {

    private RatingsPercentageCalculator ratingsPercentageCalculator;

    @Mock
    private ScoreDescriptorService scoreDescriptorService;

    @Mock
    private EstablishmentsService establishmentsService;

    private String localAuthorityId;

    @Before
    public void setup() {
        ratingsPercentageCalculator = new RatingsPercentageCalculator(establishmentsService, scoreDescriptorService);
        localAuthorityId = "197";
    }

    @Test
    public void shouldGetRatingsPercentage() throws IOException {

        when(establishmentsService.getEstablishments(localAuthorityId,1)).thenReturn(getEstablishments());
        when(establishmentsService.getEstablishments(localAuthorityId,2)).thenReturn(null);
        when(scoreDescriptorService.getScoreDecriptor()).thenReturn(getScoreDescriptors());

        List<Ratings> ratings =ratingsPercentageCalculator.calculate(localAuthorityId);
        assertNotNull(ratings);
        assertEquals(1, ratings.size());
        assertEquals("Pass", ratings.get(0).getRating());
        assertEquals(new Double(100.0), ratings.get(0).getValue());

        verify(establishmentsService, atLeast(1)).getEstablishments(localAuthorityId,1);
        verify(establishmentsService, atLeast(1)).getEstablishments(localAuthorityId,2);
        verify(scoreDescriptorService, atLeast(1)).getScoreDecriptor();

    }

    @Test
    public void shouldGetNullRatingsWhenEstablishmentsAreEmpty() throws IOException {

        when(establishmentsService.getEstablishments(localAuthorityId,1)).thenReturn(null);
        when(scoreDescriptorService.getScoreDecriptor()).thenReturn(getScoreDescriptors());

        List<Ratings> ratings =ratingsPercentageCalculator.calculate(localAuthorityId);
        assertNull(ratings);

        verify(establishmentsService, atLeast(1)).getEstablishments(localAuthorityId,1);
        verify(scoreDescriptorService, atLeast(1)).getScoreDecriptor();


    }

    @Test
    public void shouldGetNullRatingsWhenScoreDescriptorsAreNull() throws IOException {

        when(establishmentsService.getEstablishments(localAuthorityId,1)).thenReturn(getEstablishments());
        when(establishmentsService.getEstablishments(localAuthorityId,2)).thenReturn(null);
        when(scoreDescriptorService.getScoreDecriptor()).thenReturn(null);

        List<Ratings> ratings =ratingsPercentageCalculator.calculate(localAuthorityId);
        assertNull(ratings);

        verify(establishmentsService, atLeast(1)).getEstablishments(localAuthorityId,1);
        verify(scoreDescriptorService, atLeast(1)).getScoreDecriptor();

    }

}
package han.jvk.spotitube.service;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.TrackDTO;
import han.jvk.spotitube.exception.APIException;
import han.jvk.spotitube.exception.NoAffectedRowsException;
import han.jvk.spotitube.exception.RestException;
import han.jvk.spotitube.exception.ServiceException;
import han.jvk.spotitube.persistance.ITrackDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class TrackServiceTest {

    @InjectMocks
    TrackService sut;

    @Mock
    ITrackDAO trackDAO;

    private List<TrackDTO> trackListHelper;
    @BeforeEach
    void setUp() {
        this.sut = new TrackService();
        this.trackListHelper = new ArrayList<>();
        trackListHelper.addAll(createTracklist());

        MockitoAnnotations.openMocks(this);
    }

    private List<TrackDTO> createTracklist(){
        List<TrackDTO> temp = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            TrackDTO dto = new TrackDTO();
            dto.setId(i);
            temp.add(dto);
        }
        return temp;
    }

}
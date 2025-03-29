package spotify.api.impl;

import org.mockito.Mock;

import spotify.retrofit.services.PlayerService;

abstract class AbstractPlayerApiRetrofitTest extends AbstractApiRetrofitTest {
    
    public PlayerApiRetrofit sut;
    
    @Mock
    public PlayerService mockedPlayerService;
}

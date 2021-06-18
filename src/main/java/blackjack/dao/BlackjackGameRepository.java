package blackjack.dao;

import blackjack.domain.BlackjackGame;

public interface BlackjackGameRepository {
    BlackjackGame create(BlackjackGame blackjackGame);

    BlackjackGame findByGameId(Long gameId);

    BlackjackGame findForAPlayerTurn(Long gameId, Long playerId);

    BlackjackGame findForDealerTurn(Long gameId);
}

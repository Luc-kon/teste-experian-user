package teste.experian.user.util;

public enum ScoreDescricao {
    INSUFICIENTE(0, 200),
    INACEITAVEL(201, 500),
    ACEITAVEL(501, 700),
    RECOMENDAVEL(701, 1000);

    private final int min;
    private final int max;

    ScoreDescricao(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public static String getDescricao(int score) {
        for (ScoreDescricao s : values()) {
            if (score >= s.min && score <= s.max) {
                return s.name();
            }
        }
        return "DESCONHECIDO";
    }
}

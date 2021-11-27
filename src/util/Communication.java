package util;

import Model.bean.Authenticated;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Communication implements Serializable {

    private String operation;
    private String nickName;
    private Map<String, Object> params;

    public Communication(String operacao) {
        this.operation = operacao;
        params = new HashMap<>();
        setNickName(new Authenticated().getLogin());
    }

    public Communication() {
        params = new HashMap<>();
    }

    public String getOperation() {
        return operation;
    }

    public void setParam(String chave, Object valor) {
        params.put(chave, valor);
    }

    public Object getParam(String chave) {
        return params.get(chave);
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

}

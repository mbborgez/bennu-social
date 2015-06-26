package org.fenixedu.bennu.social.exception;

public class AccessTokenNotProvidedException extends Exception {

    private static final long serialVersionUID = -6434508424172449315L;

    public AccessTokenNotProvidedException(String answer) {
        super();
        this.answer = answer;
    }

    private String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}

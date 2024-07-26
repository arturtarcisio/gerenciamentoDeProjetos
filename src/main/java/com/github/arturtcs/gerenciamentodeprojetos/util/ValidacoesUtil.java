package com.github.arturtcs.gerenciamentodeprojetos.util;

import com.github.arturtcs.gerenciamentodeprojetos.exceptions.CpfInvalidoException;
import com.github.arturtcs.gerenciamentodeprojetos.exceptions.EmailInvalidoException;

public class ValidacoesUtil {

    public static void validarCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            throw new CpfInvalidoException("O CPF deve ter exatamente 11 dígitos.");
        }
    }

    public static void validarEmail(String email) {
        if (email == null || !email.contains("@") || !email.endsWith(".com")) {
            throw new EmailInvalidoException("O e-mail deve conter '@' e terminar com '.com'.");
        }
    }
}

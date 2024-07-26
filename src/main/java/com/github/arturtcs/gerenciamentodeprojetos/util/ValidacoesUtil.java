package com.github.arturtcs.gerenciamentodeprojetos.util;

import com.github.arturtcs.gerenciamentodeprojetos.exceptions.CpfInvalidoException;
import com.github.arturtcs.gerenciamentodeprojetos.exceptions.EmailInvalidoException;
import com.github.arturtcs.gerenciamentodeprojetos.exceptions.NomeObrigatorioException;
import io.micrometer.common.util.StringUtils;

public class ValidacoesUtil {

    public static void validarCpf(String cpf) {
        if (StringUtils.isEmpty(cpf) || cpf.length() != 11) {
            throw new CpfInvalidoException("O CPF deve ter exatamente 11 dígitos.");
        }
    }

    public static void validarEmail(String email) {
        if (StringUtils.isEmpty(email) || !email.contains("@") || !email.endsWith(".com")) {
            throw new EmailInvalidoException("O e-mail deve conter '@' e terminar com '.com'.");
        }
    }

    public static void validarNome(String nome) {
        if (StringUtils.isEmpty(nome))
            throw new NomeObrigatorioException("O nome deve ser informado.");
    }
}

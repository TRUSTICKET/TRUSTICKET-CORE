package com.trusticket.trusticketcore.common.exception.custom;

import com.trusticket.trusticketcore.common.ErrorDefineCode;
import lombok.Getter;

@Getter
public class BasicCustomException500 extends RuntimeException {
    private ErrorDefineCode code;

    public BasicCustomException500(ErrorDefineCode code) {
        super(code.getMessage());
        this.code = code;

    }
}



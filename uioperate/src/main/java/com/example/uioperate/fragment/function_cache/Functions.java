package com.example.uioperate.fragment.function_cache;

import androidx.annotation.NonNull;

import java.util.HashMap;

public class Functions {
    public static abstract class Function {
        public String mFunctionName;

        public Function(String mFunctionName) {
            this.mFunctionName = mFunctionName;
        }
    }

    /**
     * 返回值 + 参数
     */
    public static abstract class FunctionWithParamAndResult<Result, Param> extends Function {

        public FunctionWithParamAndResult(String mFunctionName) {
            super(mFunctionName);
        }

        public abstract Result function(Param data);
    }

    /**
     * 返回值
     */
    public static abstract class FunctionWithResult<Result> extends Function {

        public FunctionWithResult(String mFunctionName) {
            super(mFunctionName);
        }

        public abstract Result function();
    }

    /**
     * 参数
     */
    public static abstract class FunctionWithParam<Param> extends Function {

        public FunctionWithParam(String mFunctionName) {
            super(mFunctionName);
        }

        public abstract void function(Param param);
    }

    /**
     * 无
     */
    public static abstract class FunctionNo extends Function {

        public FunctionNo(String mFunctionName) {
            super(mFunctionName);
        }

        public abstract void function();
    }

    private HashMap<String, FunctionWithParamAndResult> mFunctionWithParamAndResult;
    private HashMap<String, FunctionWithParam> mFunctionWithParam;
    private HashMap<String, FunctionWithResult> mFunctionWithResult;
    private HashMap<String, FunctionNo> mFunctionNo;

    public Functions addFunction(FunctionWithParamAndResult function) {
        if (mFunctionWithParamAndResult == null) {
            mFunctionWithParamAndResult = new HashMap<>(1);
        }
        mFunctionWithParamAndResult.put(function.mFunctionName, function);
        return this;
    }

    public Functions addFunction(FunctionWithParam function) {
        if (mFunctionWithParam == null) {
            mFunctionWithParam = new HashMap<>(1);
        }
        mFunctionWithParam.put(function.mFunctionName, function);
        return this;
    }

    public Functions addFunction(FunctionWithResult function) {
        if (mFunctionWithResult == null) {
            mFunctionWithResult = new HashMap<>(1);
        }
        mFunctionWithResult.put(function.mFunctionName, function);
        return this;
    }

    public Functions addFunction(FunctionNo function) {
        if (mFunctionNo == null) {
            mFunctionNo = new HashMap<>(1);
        }
        mFunctionNo.put(function.mFunctionName, function);
        return this;
    }

    public void invokeFunc(@NonNull String funcName) throws FunctionException {
        if (mFunctionNo == null)
            throw new FunctionException("map还没有");

        FunctionNo f = mFunctionNo.get(funcName);
        if (f == null) {
            throw new FunctionException("map中没有此函数");
        }

        f.function();
    }

    public <Result> Result invokeFuncWithResult(@NonNull String funcName, @NonNull Class<Result> result) throws FunctionException {
        if (mFunctionWithResult == null)
            throw new FunctionException("map还没有");

        FunctionWithResult f = mFunctionWithResult.get(funcName);
        if (f == null) {
            throw new FunctionException("map中没有此函数");
        }

        return result.cast(f.function());
    }

    public <Param> void invokeFuncWithParam(@NonNull String funcName, @NonNull Param param) throws FunctionException {
        if (mFunctionWithParam == null)
            throw new FunctionException("map还没有");

        FunctionWithParam f = mFunctionWithParam.get(funcName);
        if (f == null) {
            throw new FunctionException("map中没有此函数");
        }

        f.function(param);
    }

    public <Result, Param> Result invokeFunctionWithParamAndResult (@NonNull String funcName, @NonNull Param param, @NonNull Class<Result> result) throws FunctionException {
        if (mFunctionWithParamAndResult == null)
            throw new FunctionException("map还没有");

        FunctionWithParamAndResult f = mFunctionWithParamAndResult.get(funcName);
        if (f == null) {
            throw new FunctionException("map中没有此函数");
        }

        return result.cast(f.function(param));
    }
}

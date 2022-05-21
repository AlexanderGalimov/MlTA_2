package ru.vsu.cs.galimov.tasks;

class ArgsParser {

    public static InputArgs parseCmdArgs(String[] args) {

        String argName = null;
        InputArgs inputArgs = new InputArgs();
        for (String arg : args) {
            if (arg.startsWith("-") && arg.length() == 2 || arg.startsWith("--")) {
                // нашли название аргумента
                argName = arg;
            } else {
                // нашли значение аргумента
                if (argName != null) {
                    if (argName.equals("-i") || argName.equals("--input-fileA")) {
                        inputArgs.setInput(arg);
                    }
                    else if (argName.equals("-o") || argName.equals("--output-file")){
                        inputArgs.setOutput(arg);
                    }
                }
            }
        }
        return inputArgs;
    }
}

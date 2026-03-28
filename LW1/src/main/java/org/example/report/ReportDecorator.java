package org.example.report;

import org.example.model.Mission;

public class ReportDecorator implements IReport{
    protected IReport wrapped;

    public ReportDecorator(IReport wrapped){
        this.wrapped = wrapped;
    }

    @Override
    public String generate(Mission mission){
        return wrapped.generate(mission);
    }
}

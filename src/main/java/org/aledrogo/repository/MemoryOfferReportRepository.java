package org.aledrogo.repository;

import org.aledrogo.entity.OfferReport;
import org.aledrogo.entity.OfferReportStatus;

import java.util.ArrayList;

public class MemoryOfferReportRepository extends OfferReportRepository {
    public ArrayList<OfferReport> reports = new ArrayList<>();


    @Override
    public OfferReport getById(int id) {
        for (OfferReport report : reports) {
            if (report.getId() == id) {
                return report;
            }
        }

        return null;
    }

    @Override
    public ArrayList<OfferReport> getAll() {
        return reports;
    }

    @Override
    public OfferReport create(OfferReport entity) {
        reports.add(entity);
        return null;
    }

    @Override
    public OfferReport update(OfferReport entity) {
        ArrayList<OfferReport> reportsForReplace = new ArrayList<>();
        for (OfferReport report : reports) {
            if (report.getId() != entity.getId()) {
                reportsForReplace.add(entity);
            }
        }
        reportsForReplace.add(entity);
        reports = reportsForReplace;
        return entity;
    }

    @Override
    public void delete(OfferReport entity) {
        ArrayList<OfferReport> reportsForReplace = new ArrayList<>();
        for (OfferReport report : reports) {
            if (report.getId() != entity.getId()) {
                reportsForReplace.add(entity);
            }
        }
        reports = reportsForReplace;
    }
}

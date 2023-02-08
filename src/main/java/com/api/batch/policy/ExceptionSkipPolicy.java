package com.api.batch.policy;

import java.sql.BatchUpdateException;
import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.dao.DuplicateKeyException;

public class ExceptionSkipPolicy implements SkipPolicy {

	@Override
	public boolean shouldSkip(Throwable t, long skipCount) throws SkipLimitExceededException {

		if (t instanceof SQLIntegrityConstraintViolationException || t instanceof DuplicateKeyException
				|| t instanceof BatchUpdateException || t instanceof ValidationException) {
			return true;
		}

		return false;
	}

}

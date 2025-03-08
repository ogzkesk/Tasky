package com.ogzkesk.database.task

import com.ogzkesk.domain.cache.DataCache
import com.ogzkesk.domain.logger.Logger
import com.ogzkesk.domain.model.Task

class TaskCreationCache(logger: Logger) : DataCache<Task>(Task.EMPTY, logger)
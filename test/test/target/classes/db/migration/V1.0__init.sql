CREATE TABLE public.taskstatus
(
    id UUID NOT NULL,
    name character varying,
    CONSTRAINT taskstatus_pkey PRIMARY KEY (id)
);

CREATE TABLE public.tasktype
(
    id UUID NOT NULL,
    name character varying,
    CONSTRAINT tasktype_pkey PRIMARY KEY (id)
);

CREATE TABLE public.task
(
    id UUID NOT NULL,
    name character varying NOT NULL,
    type UUID NOT NULL,
    progress integer NOT NULL,
    status UUID NOT NULL,
    starttime date NOT NULL,
    endtime date,
    updatetime date,
    CONSTRAINT task_pkey PRIMARY KEY (id),
    CONSTRAINT task_taskstatus_foreign_key FOREIGN KEY (status)
        REFERENCES public.taskstatus (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT task_tasktype_foreign_key FOREIGN KEY (type)
        REFERENCES public.tasktype (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);


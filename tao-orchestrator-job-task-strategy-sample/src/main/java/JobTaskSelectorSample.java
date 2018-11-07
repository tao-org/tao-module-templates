import ro.cs.tao.execution.model.ExecutionJob;
import ro.cs.tao.execution.model.ExecutionTask;
import ro.cs.tao.execution.model.TaskSelector;
import ro.cs.tao.workflow.WorkflowNodeDescriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class JobTaskSelectorSample implements TaskSelector<ExecutionJob> {

    private Function<Long, WorkflowNodeDescriptor> workflowProvider;
    private BiFunction<Long, Long, ExecutionTask> taskByNodeProvider;

    @Override
    public void setWorkflowProvider(Function<Long, WorkflowNodeDescriptor> workflowProvider) {
        this.workflowProvider = workflowProvider;
    }

    @Override
    public void setTaskByNodeProvider(BiFunction<Long, Long, ExecutionTask> taskByNodeProvider) {
        this.taskByNodeProvider = taskByNodeProvider;
    }

    @Override
    public Class<ExecutionJob> getTaskContainerClass() {
        return ExecutionJob.class;
    }

    @Override
    public List<ExecutionTask> chooseNext(ExecutionJob job, ExecutionTask currentTask) {
        List<ExecutionTask> next = null;
        List<ExecutionTask> tasks = job.orderedTasks();
        if (tasks != null && tasks.size() > 0) {
            next = new ArrayList<>();
            // SELECTION LOGIC HERE
        }
        return next;
    }
}

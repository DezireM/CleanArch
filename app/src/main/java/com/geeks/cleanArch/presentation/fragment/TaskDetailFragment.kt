import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.geeks.cleanArch.TaskUI
import com.geeks.cleanArch.databinding.FragmentTaskDetailBinding
import kotlinx.coroutines.launch

@Suppress("UNREACHABLE_CODE")
class TaskDetailFragment : Fragment() {

    private lateinit var binding: FragmentTaskDetailBinding
    private val viewModel: MainActivityViewModel by viewModels()
    private var taskId: Int = -1
    private var taskUI: TaskUI? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
        arguments?.let {
            taskId = it.getInt("taskId")
        }
        viewModel.viewModelScope.launch {
            viewModel.getTask(id)

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI()
        setUpListeners()

    }

    private fun setUpListeners() {
        binding.btnSaveTask.setOnClickListener {
            val updatedTask = taskUI?.copy(
                taskName = binding.etTaskName.text.toString(),
                taskDate = binding.etTaskDate.text.toString())
            updatedTask?.let {
                viewModel.updateTask(it)
                findNavController().navigateUp()
            }
        }
    }

    private fun updateUI() {
        binding.etTaskName.setText(taskUI?.taskName)
        binding.etTaskDate.setText(taskUI?.taskDate)
        taskUI?.taskPhoto?.let {
            binding.addPhoto.setImageURI(Uri.parse(it))
        }
    }
}
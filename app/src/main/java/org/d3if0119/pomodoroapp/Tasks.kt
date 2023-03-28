package org.d3if0119.pomodoroapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.d3if0119.pomodoroapp.databinding.FragmentTasksBinding

class Tasks : Fragment() {
    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!
    private val itemList = mutableListOf<String>()
    private val adapter by lazy { ArrayAdapter(requireContext(), android.R.layout.simple_list_item_multiple_choice, itemList) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.add.setOnClickListener {
            val task = binding.addText.editText?.text.toString().trim()
            if (task.isNotEmpty()) {
                itemList.add(task)
                binding.listView.adapter = adapter
                adapter.notifyDataSetChanged()
                binding.addText.editText?.text?.clear()
            } else {
                Toast.makeText(requireContext(), "Task tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }

        binding.delete.setOnClickListener {
            val positions = binding.listView.checkedItemPositions
            var i = positions.size() - 1
            while (i >= 0) {
                if (positions.valueAt(i)) {
                    val position = positions.keyAt(i)
                    adapter.remove(itemList[position])
                }
                i--
            }
            positions.clear()
            adapter.notifyDataSetChanged()
        }

        binding.clear.setOnClickListener {
            itemList.clear()
            adapter.notifyDataSetChanged()
        }

        binding.listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedItem = itemList[position]
            android.widget.Toast.makeText(requireContext(), "Anda memilih item $selectedItem", android.widget.Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



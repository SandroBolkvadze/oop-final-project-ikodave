USE ikodave;

-- sum-of-two-numbers
UPDATE problems SET
  input_spec = 'Two integers a and b separated by a space. Bounds: -2,000,000,000 ≤ a, b ≤ 2,000,000,000.',
  output_spec = 'A single integer, the sum of a and b.'
WHERE problem_title = 'sum-of-two-numbers';

-- maximum-of-two-numbers
UPDATE problems SET
  input_spec = 'Two integers a and b separated by a space. Bounds: -2,000,000,000 ≤ a, b ≤ 2,000,000,000.',
  output_spec = 'A single integer, the maximum of a and b.'
WHERE problem_title = 'maximum-of-two-numbers';

-- factorial-calculation
UPDATE problems SET
  input_spec = 'A single non-negative integer n. Bounds: 0 ≤ n ≤ 12.',
  output_spec = 'A single integer, n! (n factorial).'
WHERE problem_title = 'factorial-calculation';

-- array-sum
UPDATE problems SET
  input_spec = 'The first line contains an integer n (1 ≤ n ≤ 100). The second line contains n integers separated by spaces, each in the range -10,000 ≤ a_i ≤ 10,000.',
  output_spec = 'A single integer, the sum of the array.'
WHERE problem_title = 'array-sum';

-- find-maximum-in-array
UPDATE problems SET
  input_spec = 'The first line contains an integer n (1 ≤ n ≤ 100). The second line contains n integers separated by spaces, each in the range -10,000 ≤ a_i ≤ 10,000.',
  output_spec = 'A single integer, the maximum element of the array.'
WHERE problem_title = 'find-maximum-in-array';

-- palindrome-check
UPDATE problems SET
  input_spec = 'A single string s of up to 100 characters, containing letters, digits, and spaces.',
  output_spec = 'Output "true" if s is a palindrome, "false" otherwise.'
WHERE problem_title = 'palindrome-check';

-- two-sum
UPDATE problems SET
  input_spec = 'The first line contains an integer n (2 ≤ n ≤ 100). The second line contains n integers (each -1,000 ≤ a_i ≤ 1,000). The third line contains an integer target (-2,000 ≤ target ≤ 2,000).',
  output_spec = 'Two integers: the indices of the two numbers that add up to target.'
WHERE problem_title = 'two-sum'; 
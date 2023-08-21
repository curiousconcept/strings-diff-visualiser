const form = document.getElementById("input_form");

form.addEventListener("submit", formSubmit);

function formSubmit(e) {
  e.preventDefault()

  const formData = new FormData();
  formData.append(
    'input_one',
    document.querySelector('input[name="input_one"]').value
  )
  formData.append(
    'input_two',
    document.querySelector('input[name="input_two"]').value
  )

  var object = {};
  formData.forEach((value, key) => object[key] = value);

  var jsonReq = JSON.stringify(object);

  const response = fetch("/v1/stringsdiff",
  {
    headers: {"Content-Type": "application/json"},
    method: "POST",
    body: jsonReq
  })
  .then(resp => resp.json())
  .then(diffRes => renderResult(diffRes))
  .catch(error => console.log(error))
}

function renderResult(diffRes) {
console.log(diffRes)
    document.getElementById('output').innerText = diffRes["diff_output"]
}
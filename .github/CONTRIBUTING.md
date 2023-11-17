# Contributing to the Knightmares 2023-24 Teamcode Repository

1. Make a branch off of main with the name of your feature. For example, if you are adding a new class for a Claw
class, you can call the branch `Claw`. If you are updating the Claw class, you can call it `ClawUpdate`.
Please note the usage of PascalCase for the branch name.

2. Make as many commits to the branch as you want. If needed, leave a short note explaining what you have accomplished
This may be forgone if the changes are quite obvious

3. Wait for someone to review and merge the PR


## File Structure

- Do your best to only change code under `TeamCode/src/main/java/org.firstinspires.ftc.teamcode/`
  - If you need to add dependencies, you can also edit gradle files as needed
- Keep auton paths inside the `auton/` folder
- Use `pipelines/` to keep different OpenCVPipelines or other detection algorithms developed throughout the season
- `teleop/` should contain different bindings for teleop and manual reset opmodes
- `utilities/` can be used for hardware classes, config files, etc.